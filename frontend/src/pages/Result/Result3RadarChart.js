import { useState } from "react";
import { Radar } from "react-chartjs-2";
import { Chart, registerables } from "chart.js";

import styled from "styled-components";

const Graph = styled.div`
  width: 450px;
  height: 400px;

  @media screen and (max-width: 768px) {
    width: 90%;
    height: 300px;
    margin: 0 auto;
  }
`;

const Result3RadarChart = (prop) => {
  Chart.register(...registerables);

  const [result, setResult] = useState(prop.result);

  // result에서 answer / problem * 100으로 백분율로 변경
  const percentByPart = {
    listening: Math.round(
      (result.Listening.answer / result.Listening.problem) * 100
    ),
    reading: Math.round((result.Reading.answer / result.Reading.problem) * 100),
    speaking: Math.round(
      (result.Speaking.answer / result.Speaking.problem) * 100
    ),
    writing: Math.round((result.Writing.answer / result.Writing.problem) * 100),
    grammar: Math.round((result.Grammar.answer / result.Grammar.problem) * 100),
  };

  console.log(percentByPart);

  const chartData = {
    labels: ["Speaking", "Reading", "Listening", "Grammar", "Writing"],
    datasets: [
      {
        label: "테스트 결과",
        backgroundColor: "gray",
        borderColor: "#83BD75",
        fill: false,
        radius: 6,
        data: [
          percentByPart.speaking,
          percentByPart.reading,
          percentByPart.listening,
          percentByPart.grammar,
          percentByPart.writing,
        ],
      },
    ],
  };

  const options = {
    maintainAspectRatio: false,
    scales: {
      r: {
        min: 0,
        max: 100,
        beginAtZero: true,
      },
    },
  };

  return (
    <Graph>
      <Radar data={chartData} options={options} />
    </Graph>
  );
};

export default Result3RadarChart;
