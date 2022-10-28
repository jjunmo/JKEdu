import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { isMobile } from "react-device-detect";

import AssignmentIcon from "@mui/icons-material/Assignment";
import ManageAccountsIcon from "@mui/icons-material/ManageAccounts";
import PaymentIcon from "@mui/icons-material/Payment";

import "App.css";

const MainDiv = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: calc(100% - 80px);
  position: relative;

  @media screen and (max-width: 768px) {
    height: calc(100% - 40px);
  }
`;

const Wrapper = styled.div``;

const Title = styled.div`
  box-sizing: border-box;
  text-align: center;
  margin-bottom: 20px;

  @media screen and (max-width: 768px) {
    margin-bottom: 10px;
  }
`;
const TitleSpanBlack = styled.span`
  font-size: 96px;
  font-family: SCDream9;
  @media screen and (max-width: 768px) {
    font-size: 48px;
  }
`;
const TitleSpanRed = styled(TitleSpanBlack)`
  color: #ff0000;
`;

const Content = styled.div`
  display: grid;
  grid-template-columns: 1fr;
  @media screen and (max-width: 768px) {
    width: 100%;
  }
`;

const LevelTestBtn = styled.button`
  box-sizing: border-box;
  border: none;
  padding: 0;
  margin: 15px auto;
  width: 400px;
  height: 80px;
  line-height: 80px;

  border-radius: 25px;
  font-size: 25px;

  background-color: #35a753;
  color: white;
  cursor: pointer;

  @media screen and (max-width: 768px) {
    width: 80%;
    height: 50px;
    line-height: 50px;
    border-radius: 15px;
    font-size: 15px;
  }
`;
const ManagementBtn = styled(LevelTestBtn)``;
const PaymentBtn = styled(LevelTestBtn)`
  background-color: #fd9f28;
`;

const BtnExplain = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Main = () => {
  const navigate = useNavigate();

  // 레벨 테스트, 학생 관리, 결제 하기로 이동하는 함수
  const movePage = (url) => {
    if (url === "/exam-privacy") {
      isMobile
        ? window.alert(
            "모바일에서는 시험을 응시할 수 없습니다.\nPC를 이용해주세요."
          )
        : navigate(`${url}`);
    } else {
      navigate(`${url}`);
    }
  };
  return (
    <MainDiv>
      <Wrapper>
        <Title>
          <TitleSpanRed>JK&nbsp;</TitleSpanRed>
          <TitleSpanBlack>Evaluation</TitleSpanBlack>
        </Title>
        <Content>
          <LevelTestBtn onClick={() => movePage("/exam-privacy")}>
            <BtnExplain>
              <span>레벨 테스트</span>
              <AssignmentIcon fontSize="large" />
            </BtnExplain>
          </LevelTestBtn>
          <ManagementBtn onClick={() => movePage("/management")}>
            <BtnExplain>
              <span>학생 관리</span>
              <ManageAccountsIcon fontSize="large" />
            </BtnExplain>
          </ManagementBtn>
          <PaymentBtn onClick={() => movePage("/payment")}>
            <BtnExplain>
              <span>결제 하기</span>
              <PaymentIcon fontSize="large" />
            </BtnExplain>
          </PaymentBtn>
        </Content>
      </Wrapper>
    </MainDiv>
  );
};

export default Main;
