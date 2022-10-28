import styled from "styled-components";
import { useNavigate } from "react-router-dom";

const Header = styled.div`
  width: 1000px;
  max-width: 1000px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 15px auto;
  font-family: SCDream9;

  @media screen and (max-width: 768px) {
    width: 90%;
  }
`;

const Name = styled.div`
  margin: 15px 0px;
  font-size: 35px;
  @media screen and (max-width: 768px) {
    width: 350px;
    font-size: 25px;
  }
`;

const CurriculumBtn = styled.button`
  border: none;
  padding: 0;
  margin: 0;
  width: 250px;
  max-width: 250px;
  height: 70px;
  color: white;
  font-family: SCDream5;
  font-size: 24px;
  border-radius: 10px;
  border: none;
  background-color: #35a753;
  cursor: pointer;

  // 프린트 시 버튼 안보이게하기
  @media print {
    display: none;
  }

  @media screen and (max-width: 768px) {
    font-size: 13px;
    width: 150px;
    height: 40px;
  }
`;

const ResultHeader = () => {
  const navigate = useNavigate();

  const movePage = () => {
    navigate("/curriculum");
  };

  return (
    <Header>
      <Name>Evaluation Report</Name>
      <CurriculumBtn onClick={movePage}>커리큘럼 / 시간표 생성</CurriculumBtn>
    </Header>
  );
};

export default ResultHeader;
